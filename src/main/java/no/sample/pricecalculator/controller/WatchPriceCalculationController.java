package no.sample.pricecalculator.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.constraints.NotEmpty;
import io.swagger.v3.oas.annotations.Operation;
import no.sample.pricecalculator.model.ErrorResponse;
import no.sample.pricecalculator.model.PriceResponse;
import no.sample.pricecalculator.service.WatchPriceCalculationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WatchPriceCalculationController {

    private final WatchPriceCalculationService watchPriceCalculationService;

    public WatchPriceCalculationController(WatchPriceCalculationService watchPriceCalculationService) {
        this.watchPriceCalculationService = watchPriceCalculationService;
    }

    @Operation(method = "POST",
            summary = "get the minimum total price of watch list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "calculate watch price", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PriceResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "unknown error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    @PostMapping(path = "/checkout", produces = "application/json", consumes = "application/json")
    public ResponseEntity<PriceResponse> checkout(@RequestBody List<@NotEmpty(message = "{invalid.watch.id}") String> watchIds) {
        return ResponseEntity.ok(new PriceResponse(watchPriceCalculationService.calculateCartPrice(watchIds)));
    }
}
