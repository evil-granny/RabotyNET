package ua.softserve.ita.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.CV;

import ua.softserve.ita.model.Education;
import ua.softserve.ita.model.Job;
import ua.softserve.ita.model.Skill;
import ua.softserve.ita.service.*;
import ua.softserve.ita.service.letter.GenerateLetter;
import ua.softserve.ita.service.pdfcreater.TestCVPDF;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

@CrossOrigin
@RestController
public class PDFController {
    private final CVService cvService;
    private final GenerateLetter generateService;
    private final TestCVPDF pdfService;


    public PDFController(CVService cvService,GenerateLetter generateService, TestCVPDF pdfService) {
        this.cvService = cvService;
        this.generateService = generateService;
        this.pdfService = pdfService;
    }

    @GetMapping(value = "/pdf/{id}")
    public CV getCV(@PathVariable("id") long id) {

       return cvService.findById(id).orElseThrow(() -> new ResourceNotFoundException("cv not found with id " + id));
    }

    @PutMapping("/updatePDF")
    public CV update(@Valid @RequestBody CV cv) {

        Set<Skill> skills = cv.getSkills();
        Set<Job> jobs = cv.getJobs();
        skills.forEach(x -> x.setCv(cv));
        jobs.forEach(x -> x.setCv(cv));
        return cvService.update(cv);

    }

    @RequestMapping(value = "/createPdf/{id}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<byte[]> createPdf(@PathVariable("id") long id,HttpServletResponse response) {

        response.setContentType("application/pdf");

        CV cv = cvService.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Vacancy with id: %d not found", id)));

        Path pathToPdf = pdfService.createPDF(cv);
        byte[] fileContent;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/pdf"));
            headers.set("Content-Disposition", "inline");
            fileContent = Files.readAllBytes(pathToPdf.toRealPath());
            generateService.sendPersonPDF(cv.getPerson(), pathToPdf.toRealPath().toString());
            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
        }



        //byte[] contents = pdfService.createPDF(cv);

        //HttpHeaders headers = new HttpHeaders();
       // headers.setContentType(MediaType.parseMediaType("application/pdf"));
       // headers.set("Content-Disposition", "inline");

        return null;
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + 4200 + request.getContextPath();
    }

}
