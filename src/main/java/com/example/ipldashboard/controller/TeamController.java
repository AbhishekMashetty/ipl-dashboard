package com.example.ipldashboard.controller;

import com.example.ipldashboard.controller.repository.MatchRepository;
import com.example.ipldashboard.controller.repository.TeamRepository;
import com.example.ipldashboard.model.Match;
import com.example.ipldashboard.model.Team;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private MatchRepository matchRepository;
    @GetMapping("/team/{teamName}")
    public Team getTeam(@PathVariable String teamName){
        System.out.println("Entered getTeam API");
        Team team = teamRepository.findByTeamName(teamName);
        Pageable pageable = PageRequest.of(0,4);
        team.setMatches(matchRepository.findLatestMatchesByTeam(teamName, 4));
        return team;
    }
    @GetMapping("/team/{teamName}/matches")
    public List<Match> getMatchesForTeam(@PathVariable String teamName, @RequestParam int year){
        System.out.println(" Entered getMatchesForTeam API");
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year+1, 1, 1);
        List<Match> temp = matchRepository.getMatchesByTeamDatesBetween(teamName, startDate, endDate);
        for( Match it: temp){
            System.out.println("entered here~");
            System.out.println(it);
        }
        return this.matchRepository.getMatchesByTeamDatesBetween(teamName, startDate, endDate);
    }
    @GetMapping("/teams")
    public Iterable<Team> getAllTeams(){
        return this.teamRepository.findAll();

    }


}
