package com.hackathon.EdTech.config;

import com.hackathon.EdTech.service.QueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class QueryController {

    private final QueryService queryService;

    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @PostMapping("/query")
    public ResponseEntity<String> query(@RequestBody Map<String, String> body) {
        String query = body.get("query");
        String response = queryService.queryFlask(query);
        return ResponseEntity.ok(response);
    }
}
