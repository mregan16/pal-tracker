package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private TimeEntryRepository timeEntryRepo;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepo = timeEntryRepository;
    }

    public ResponseEntity create(TimeEntry timeEntryToCreate) {
        TimeEntry entry = timeEntryRepo.create(timeEntryToCreate);
        return new ResponseEntity<>(entry, HttpStatus.CREATED);
    }

    public ResponseEntity<TimeEntry> read(Long timeEntryId) {
        TimeEntry entry = timeEntryRepo.find(timeEntryId);
        if(entry != null)
            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(entry, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<TimeEntry>> list() {
        return new ResponseEntity<>(timeEntryRepo.list(), HttpStatus.CREATED);
    }

    //fix
    public ResponseEntity update(Long timeEntryId, TimeEntry any) {
        TimeEntry entry  timeEntryRepo.update(timeEntryId, any);
        if(entry != null)
            return new ResponseEntity(entry, HttpStatus.CREATED);
        else
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity delete(Long timeEntryId) {
        timeEntryRepo.delete(timeEntryId);

        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }
}
