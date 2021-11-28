package ua.com.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.com.dto.VacancyDto;
import ua.com.service.vacancy.BookmarkService;

@RestController
@RequestMapping("/bookmarks")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @Autowired
    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @GetMapping("/findBookmarks/{userId}/{first}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VacancyDto> getBookmarks(@PathVariable("userId") Long userId, @PathVariable("first") int first) {
        return ResponseEntity.ok().body(bookmarkService.findAllBookmarksByUserId(userId, first));
    }

    @PostMapping("/createBookmark/{vacancyId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiOperation(value = "Create bookmark")
    public ResponseEntity<?> createBookmark(@PathVariable("vacancyId") Long vacancyId, @RequestBody Long userId) {
        try {
            bookmarkService.save(vacancyId, userId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/deleteBookmark/{vacancyId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiOperation(value = "Remove vacancy from bookmarks")
    public ResponseEntity<?> deleteBookmark(@PathVariable("vacancyId") Long vacancyId, @RequestBody Long userId) {
        try {
            bookmarkService.deleteBookmarkByUserIdAndVacancyId(userId, vacancyId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

}
