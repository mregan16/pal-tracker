package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository{

    private Long curId = 1L;
    private HashMap<Long, TimeEntry> entryMap = new HashMap<>();

    @Override
    public TimeEntry create(TimeEntry any){
        Long id = curId++;

        TimeEntry entry = new TimeEntry(id, any.getProjectId(), any.getUserId(), any.getDate(), any.getHours());

        entryMap.put(id, entry);
        return entry;

    }

    @Override
    public TimeEntry find(Long timeEntryId){
        return entryMap.get(timeEntryId);
    }

    @Override
    public List<TimeEntry> list(){
        return new ArrayList<>(entryMap.values());
    }

    @Override
    public TimeEntry update(Long id, TimeEntry any){
        if(find(id) == null)
            return null;

        TimeEntry entry = new TimeEntry(id, any.getProjectId(), any.getUserId(), any.getDate(), any.getHours());

        entryMap.replace(id, entry);
        return entry;
    }

    @Override
    public void delete(Long timeEntryId){
        entryMap.remove(timeEntryId);
    }

}
