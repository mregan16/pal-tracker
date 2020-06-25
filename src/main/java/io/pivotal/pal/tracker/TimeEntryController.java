package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private TimeEntryRepository timeEntryRepo;
    private final Counter counter;
    private final DistributionSummary summary;

    public TimeEntryController(TimeEntryRepository timeEntryRepo, MeterRegistry meterRegistry) {
        this.timeEntryRepo = timeEntryRepo;
        this.counter = meterRegistry.counter("timeEntry.counter");
        this.summary = meterRegistry.summary("timeEntry.summary");
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry) {
        TimeEntry newEntry = timeEntryRepo.create(timeEntry);
        counter.increment();
        summary.record(timeEntryRepo.list().size());
        return new ResponseEntity<>(newEntry, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable Long id) {
        TimeEntry entry = timeEntryRepo.find(id);
        if(entry != null) {
            counter.increment();
            return new ResponseEntity<>(entry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        counter.increment();
        return new ResponseEntity<>(timeEntryRepo.list(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable Long id, @RequestBody TimeEntry timeEntry) {
        TimeEntry newEntry = timeEntryRepo.update(id, timeEntry);
        if(newEntry != null) {
            counter.increment();
            return new ResponseEntity<>(newEntry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{timeEntryId}")
    public ResponseEntity delete(@PathVariable Long timeEntryId) {
        timeEntryRepo.delete(timeEntryId);
        counter.increment();
        summary.record(timeEntryRepo.list().size());
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }

}
