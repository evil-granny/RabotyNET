package ua.softserve.ita.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.Resume;

import ua.softserve.ita.model.Job;
import ua.softserve.ita.model.Skill;
import ua.softserve.ita.service.*;
import ua.softserve.ita.service.letter.GenerateLetter;
import ua.softserve.ita.service.pdfcreater.CreateCvPdf;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ua.softserve.ita.utility.LoggedUserUtil.getLoggedUser;

@RestController
public class PDFController {

    private static final Logger LOGGER = Logger.getLogger(PDFController.class.getName());

    private final ResumeService resumeService;

    private final GenerateLetter generateService;

    private final CreateCvPdf pdfService;

    public PDFController(ResumeService resumeService, GenerateLetter generateService, CreateCvPdf pdfService) {

        this.resumeService = resumeService;

        this.generateService = generateService;

        this.pdfService = pdfService;
    }

    @GetMapping(value = "/pdf/{id}")
    public Resume getCV(@PathVariable("id") long id) {

        Long userID = getLoggedUser().get().getUserId();

      return resumeService.findById(id).orElseThrow(() -> new ResourceNotFoundException("resume not found with id " + id));

    }

    @PutMapping("/pdf/updatePDF")
    public Resume update(@Valid @RequestBody Resume resume) {

        Set<Skill> skills = resume.getSkills();

        Set<Job> jobs = resume.getJobs();

        skills.forEach(x -> x.setResume(resume));

        jobs.forEach(x -> x.setResume(resume));

        return resumeService.update(resume);

    }

    @RequestMapping(value = "/pdf/createPdf/{id}&{send}", method = RequestMethod.GET, produces = "application/pdf")
    //public ResponseEntity<byte[]> createPdf(@PathVariable("id") long id, HttpServletResponse response) {

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

            if (send) generateService.sendPersonPDF(resume.getPerson(), pathToPdf.toRealPath().toString());

            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);

        } catch (IOException e) {

            LOGGER.log(Level.SEVERE, e.toString(), e);

        }

        return null;
    }

}
