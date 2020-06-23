package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository{

    private long curId = 1L;
    private HashMap<Long, TimeEntry> entryMap = new HashMap<>();

    public TimeEntry create(TimeEntry any){
        Long id = curId++;

        TimeEntry entry = new TimeEntry(id, any.getProjectId(), any.getUserId(), any.getDate(), any.getHours());

        entryMap.put(id, entry);
        return entry;

    }

    public TimeEntry find(Long timeEntryId){
        return entryMap.get(timeEntryId);
    }

    public List<TimeEntry> list(){
        return new ArrayList<>(entryMap.values());
    }

    public TimeEntry update(Long id, TimeEntry any){
        if(find(id) == null)
            return null;

        TimeEntry entry = new TimeEntry(id, any.getProjectId(), any.getUserId(), any.getDate(), any.getHours());

        entryMap.replace(id, entry);
        return entry;
    }

    public void delete(Long timeEntryId){
        entryMap.remove(timeEntryId);
    }

}
