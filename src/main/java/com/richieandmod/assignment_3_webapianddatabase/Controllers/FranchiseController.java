package com.richieandmod.assignment_3_webapianddatabase.Controllers;

import com.richieandmod.assignment_3_webapianddatabase.Models.CommonResponse;
import com.richieandmod.assignment_3_webapianddatabase.Models.Franchise;
import com.richieandmod.assignment_3_webapianddatabase.Repositories.FranchiseRepository;
import com.richieandmod.assignment_3_webapianddatabase.Services.FranchiseServiceImpl;
import com.richieandmod.assignment_3_webapianddatabase.Utilities.Command;
import com.richieandmod.assignment_3_webapianddatabase.Utilities.Logger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/franchises")
public class FranchiseController {

    @Autowired
    FranchiseRepository franchiseRepository;

    @Autowired
    private FranchiseServiceImpl franchiseServiceImpl;

    //Get all franchises
    @Operation(summary = "Get all franchises that are present in db")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all franchises",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Franchise.class))})
    })
    @GetMapping("/all")
    public ResponseEntity<CommonResponse> getAllFranchises(HttpServletRequest request) {
        Command cmd = new Command(request);

        CommonResponse commonResponse = new CommonResponse();
        commonResponse.data = franchiseRepository.findAll();
        commonResponse.message = "All franchises";

        HttpStatus resp = HttpStatus.OK;

        cmd.setResult(resp);
        Logger.getInstance().logCommand(cmd);
        return new ResponseEntity<>(commonResponse, resp);
    }

    //Get franchise by id
    @Operation(summary = "Get a franchise by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "One franchise has been found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Franchise.class))}),
            @ApiResponse(responseCode = "404", description = "Franchise not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> getFranchiseById(@Parameter(description = "id of the franchise that needs to be searched")
                                                                       HttpServletRequest request, @PathVariable Integer id) {
        Command cmd = new Command(request);

        CommonResponse commonResponse = new CommonResponse();
        HttpStatus resp;

        if (franchiseRepository.existsById(id)) {
            commonResponse.data = franchiseRepository.findById(id);
            commonResponse.message = "Franchise with id: " + id;
            resp = HttpStatus.OK;
        } else {
            commonResponse.data = null;
            commonResponse.message = "Franchise not found";
            resp = HttpStatus.NOT_FOUND;
        }

        cmd.setResult(resp);
        Logger.getInstance().logCommand(cmd);
        return new ResponseEntity<>(commonResponse, resp);
    }

    //Get all movies in a franchise
    @Operation(summary = "Get all movies in a franchise by franchise name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The following movies have been found for this franchise",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Franchise.class))}),
            @ApiResponse(responseCode = "404", description = "Franchise not found",
                    content = @Content)
    })
    @GetMapping("/{name}/movies")
    public ResponseEntity<CommonResponse> getAllMoviesInFranchiseByName(@Parameter(description = "name of the franchise that needs to be searched")
                                                                                    HttpServletRequest request, @PathVariable String name) {
        Command cmd = new Command(request);
        CommonResponse commonResponse = new CommonResponse();
        HttpStatus resp;
        List<String> movieNames;

        if (franchiseRepository.existsFranchiseByName(name)) {
            movieNames = franchiseServiceImpl.getAllMoviesInFranchise(name);
            commonResponse.data = movieNames;
            commonResponse.message = "All movies published by franchise: " + name;
            resp = HttpStatus.OK;
        } else {
            commonResponse.data = null;
            commonResponse.message = "Franchise not found";
            resp = HttpStatus.NOT_FOUND;
        }

        cmd.setResult(resp);
        Logger.getInstance().logCommand(cmd);
        return new ResponseEntity<>(commonResponse, resp);
    }

    @Operation(summary = "Get all actors in a franchise by franchise name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The following actors are starring in this franchise",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Franchise.class))}),
            @ApiResponse(responseCode = "404", description = "Franchise not found",
                    content = @Content)
    })
    @GetMapping("/{name}/actors/all")
    public ResponseEntity<CommonResponse> getAllActorsInFranchise(@Parameter(description = "name of the franchise that needs to be searched")
                                                                              HttpServletRequest request, @PathVariable String name) {
        Command cmd = new Command(request);
        CommonResponse commonResponse = new CommonResponse();
        HttpStatus resp;
        List<String> actorsInFranchise;

        if (franchiseRepository.existsFranchiseByName(name)) {
            actorsInFranchise = franchiseServiceImpl.getAllActorsInFranchise(name);
            commonResponse.data = actorsInFranchise;
            commonResponse.message = "All actors starring in this franchise: " + name;
            resp = HttpStatus.OK;
        } else {
            commonResponse.data = null;
            commonResponse.message = "Franchise not found";
            resp = HttpStatus.NOT_FOUND;
        }

        cmd.setResult(resp);
        Logger.getInstance().logCommand(cmd);
        return new ResponseEntity<>(commonResponse, resp);
    }


    //Update movies in franchise
    @Operation(summary = "Update the movies in a franchise")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The movies have been updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Franchise.class))}),
            @ApiResponse(responseCode = "404", description = "Movie not found",
                    content = @Content)
    })
    @PutMapping("/{id}/movies/update/")
    public ResponseEntity<CommonResponse> updateMoviesInFranchise(@Parameter(description = "id of the franchise that needs to be updated")
                                                                              HttpServletRequest request, @PathVariable Integer id,
                                                                  @RequestBody Integer[] franchiseId) {
        Command cmd = new Command(request);
        CommonResponse commonResponse = new CommonResponse();
        HttpStatus resp;

        if (franchiseRepository.existsById(id)) {
            commonResponse.data = franchiseServiceImpl.updateMoviesInFranchise(id, franchiseId);
            commonResponse.message = "movies in franchise with id: " + id + " have been updated";
            resp = HttpStatus.OK;
        } else {
            commonResponse.data = null;
            commonResponse.message = "movie not found";
            resp = HttpStatus.NOT_FOUND;
        }

        cmd.setResult(resp);
        Logger.getInstance().logCommand(cmd);
        return new ResponseEntity<>(commonResponse, resp);
    }

    //Create franchise and save to DB
    @Operation(summary = "Create new franchise")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created a new franchise",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Franchise.class))})
    })
    @PostMapping("/create")
    public ResponseEntity<CommonResponse> createFranchise(HttpServletRequest request, HttpServletResponse response,
                                                          @RequestBody Franchise franchise) {
        Command cmd = new Command(request);

        franchise = franchiseRepository.save(franchise);

        CommonResponse commonResponse = new CommonResponse();
        commonResponse.data = franchise;
        commonResponse.message = "New franchises created, with id: " + franchise.id;

        HttpStatus resp = HttpStatus.CREATED;

        response.addHeader("Location", "/franchises/" + franchise.id);

        cmd.setResult(resp);
        Logger.getInstance().logCommand(cmd);
        return new ResponseEntity<>(commonResponse, resp);
    }

    //Update existing franchise by id
    @Operation(summary = "Update the existing franchise on id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The franchise has been updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Franchise.class))}),
            @ApiResponse(responseCode = "404", description = "Franchise not found",
                    content = @Content)
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<CommonResponse> updateFranchise(@Parameter(description = "id of the franchise that needs to be updated")
                                                                      HttpServletRequest request, @PathVariable Integer id,
                                                          @RequestBody Franchise newFranchise) {
        Command cmd = new Command(request);

        CommonResponse commonResponse = new CommonResponse();
        HttpStatus resp;

        if (franchiseRepository.existsById(id)) {
            Optional<Franchise> franchiseRepo = franchiseRepository.findById(id);
            Franchise franchise = franchiseRepo.get();

            if (newFranchise.name != null) {
                franchise.name = newFranchise.name;
            }
            if (newFranchise.description != null) {
                franchise.description = newFranchise.description;
            }
            if (newFranchise.movies != null && !newFranchise.movies.isEmpty()) {
                franchise.movies.addAll(newFranchise.movies);
            }
            franchiseRepository.save(franchise);

            commonResponse.data = franchise;
            commonResponse.message = "Updated franchise with id: " + franchise.id;
            resp = HttpStatus.OK;
        } else {
            commonResponse.message = "Franchise with id " + id + " not found";
            resp = HttpStatus.NOT_FOUND;
        }

        cmd.setResult(resp);
        Logger.getInstance().logCommand(cmd);
        return new ResponseEntity<>(commonResponse, resp);
    }

    //Delete franchise by id
    @Operation(summary = "Delete the franchise on id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The franchise has been deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Franchise.class))}),
            @ApiResponse(responseCode = "404", description = "Franchise not found",
                    content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CommonResponse> deleteFranchise(@Parameter(description = "id of the franchise that needs to be deleted")
                                                                      HttpServletRequest request, @PathVariable Integer id) {
        Command cmd = new Command(request);

        CommonResponse commonResponse = new CommonResponse();
        HttpStatus resp;

        if (franchiseRepository.existsById(id)) {
            franchiseRepository.deleteById(id);
            commonResponse.message = "Deleted franchise with id: " + id;
            resp = HttpStatus.OK;
        } else {
            commonResponse.message = "Franchise with id " + id + " not found";
            resp = HttpStatus.NOT_FOUND;
        }

        cmd.setResult(resp);
        Logger.getInstance().logCommand(cmd);
        return new ResponseEntity<>(commonResponse, resp);
    }
}
