package pt.isec.pd.spring_boot.exemplo3.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pt.isec.pd.spring_boot.exemplo3.models.Attendance;
import pt.isec.pd.spring_boot.exemplo3.models.Event;
import pt.isec.pd.spring_boot.manageDB.DbOperations;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventsController {

    @GetMapping("get")
    public ResponseEntity<?> getEvent(Authentication auth) {
        DbOperations db = DbOperations.getInstance();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("SCOPE_ADMIN"))) {
            List<Event> events = db.getAllEvents();
            return ResponseEntity.ok(events);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("create")
    public ResponseEntity<String> createEvent(
            Authentication auth,
            @RequestBody Event event
    ) {
        DbOperations db = DbOperations.getInstance();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("SCOPE_ADMIN"))) {
            db.createEvent(event.getName(), event.getLocation(), event.getDate(), event.getStartTime(), event.getEndTime());
            return new ResponseEntity<>("Event created successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error creating the event", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("consultAttendance")
    public ResponseEntity<?> consultAttendance(
            Authentication auth,
            @RequestParam(value="event") int eventId
    ) {
        DbOperations db = DbOperations.getInstance();

        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("SCOPE_ADMIN"))) {
            List<Attendance> attendance = db.getEventAttendance(eventId);
            return ResponseEntity.ok(attendance);
        } else {
            return new ResponseEntity<>("User not authorized", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("delete")
    public ResponseEntity<?> deleteEvent(
            Authentication auth,
            @RequestParam(value="event") int eventId
    ) {
        DbOperations db = DbOperations.getInstance();

        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("SCOPE_ADMIN"))) {
            db.deleteEvent(eventId);
            return new ResponseEntity<>("Event "+ eventId + " remove successfully",HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not authorized", HttpStatus.FORBIDDEN);
        }
    }
}
