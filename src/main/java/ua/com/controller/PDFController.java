package ua.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.exception.ResourceNotFoundException;
import ua.com.model.*;
import ua.com.service.PdfResumeService;
import ua.com.service.ResumeService;
import ua.com.service.letter.GenerateLetter;
import ua.com.service.pdfcreator.CreateResumePdf;
import ua.com.utility.LoggedUserUtil;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class PDFController {

    private static final Logger LOGGER = Logger.getLogger(PDFController.class.getName());

    private final ResumeService resumeService;
    private final GenerateLetter generateService;
    private final CreateResumePdf pdfService;
    private final PdfResumeService pdfResumeService;

    @Autowired
    public PDFController(ResumeService resumeService, GenerateLetter generateService, CreateResumePdf pdfService, PdfResumeService pdfResumeService) {
        this.resumeService = resumeService;
        this.generateService = generateService;
        this.pdfService = pdfService;
        this.pdfResumeService = pdfResumeService;
    }

    @GetMapping(value = "/pdf/{id}")
    public Resume getCV(@PathVariable("id") long id) {
        return resumeService.findById(id).orElseThrow(() -> new ResourceNotFoundException("resume not found with id " + id));
    }

    @GetMapping(value = "/pdf")
    public Resume getCVByUser() {
        return resumeService.findByUserId(LoggedUserUtil.getLoggedUser().get().getUserId()).orElseThrow(() -> new ResourceNotFoundException("CV with id: %d not found"));
    }

    @GetMapping(value = "/pdf/sendEmail")
    public boolean sendEmail() {
        PdfResume pdfResume = pdfResumeService.findByUserId(LoggedUserUtil.getLoggedUser().get().getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("CV with id: %d not found"));
        generateService.sendPersonPDF(pdfResume.getPerson(), pdfResume.getPath());
        return true;
    }


    @PutMapping("/pdf/updatePDF")
    public Resume update(@Valid @RequestBody Resume resume) {
        Set<Skill> skills = resume.getSkills();
        Set<Job> jobs = resume.getJobs();
        Set<Vacancy> vacancies = resume.getVacancies();
        vacancies.clear();
        skills.forEach(x -> x.setResume(resume));
        jobs.forEach(x -> x.setResume(resume));

        return resumeService.update(resume);
    }

    @GetMapping(value = "/pdf/createPdf/{id}&{send}", produces = "application/pdf")
    public ResponseEntity<byte[]> createPdf(@PathVariable("id") long id, @PathVariable("send") boolean send, HttpServletResponse response) {
        response.setContentType("application/pdf");
        Resume resume = resumeService.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Vacancy with id: %d not found", id)));
        Path pathToPdf = pdfService.createPDF(resume);
        byte[] fileContent;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/pdf"));
            headers.set("Content-Disposition", "inline");
            fileContent = Files.readAllBytes(pathToPdf.toRealPath());
            if (send)
                generateService.sendPersonPDF(resume.getPerson(), pathToPdf.toRealPath().toString());
            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        return null;
    }

}
