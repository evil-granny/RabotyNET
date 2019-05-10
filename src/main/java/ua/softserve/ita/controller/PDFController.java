package ua.softserve.ita.controller;

import org.springframework.web.bind.annotation.*;
import ua.softserve.ita.exception.ResourceNotFoundException;
import ua.softserve.ita.model.CV;

import ua.softserve.ita.service.*;
import ua.softserve.ita.service.letter.GenerateLetter;
import ua.softserve.ita.service.pdfcreater.TestCVPDF;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
            cvService.update(cv);
            pdfService.createPDF(cv);
            return cv;

    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + 4200 + request.getContextPath();
    }

}
