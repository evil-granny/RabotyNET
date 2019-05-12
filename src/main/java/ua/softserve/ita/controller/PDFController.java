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
import java.util.Set;

@CrossOrigin
@RestController
public class PDFController {
    private final CVService cvService;
    private final GenerateLetter letterService;
    private final TestCVPDF pdfService;


    public PDFController(CVService cvService,GenerateLetter letterService, TestCVPDF pdfService) {
        this.cvService = cvService;
        this.letterService = letterService;
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

    @RequestMapping(value = "/createPdf", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<byte[]> createPdf(HttpServletResponse response) {

        response.setContentType("application/pdf");

        long id=1;
        CV cv = cvService.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Vacancy with id: %d not found", id)));
        byte[] contents = pdfService.createPDF(cv);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.set("Content-Disposition", "inline");

        return new ResponseEntity<>(contents, headers, HttpStatus.OK);
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + 4200 + request.getContextPath();
    }

}
