package bg.softuni.pathfinder.web;

import bg.softuni.pathfinder.model.CategoryType;
import bg.softuni.pathfinder.model.Level;
import bg.softuni.pathfinder.service.RouteService;
import bg.softuni.pathfinder.service.dto.RouteShortInfoDTO;
import bg.softuni.pathfinder.web.dto.AddRouteDTO;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }


    @GetMapping("/routes")
    @PreAuthorize("hasRole('ADMIN')")
    public String routes(Model model) {
//        RouteShortInfoDTO randomRoute = routeService.getRandomRoute();
        List<RouteShortInfoDTO> routes = routeService.getAll();

        model.addAttribute("allRoutes", routes);

        return "routes";
    }

    @GetMapping("add-route")
    public ModelAndView addRoute() {
        ModelAndView modelAndView = new ModelAndView("add-route");

        modelAndView.addObject("route", new RouteShortInfoDTO());
        modelAndView.addObject("levels", Level.values());
        modelAndView.addObject("categoryTypes", CategoryType.values());

        return modelAndView;
    }

    @ModelAttribute("routeData")
    public AddRouteDTO routeData() {
        return new AddRouteDTO();
    }

    @PostMapping("add-route")
    public String addRoute(@Valid AddRouteDTO data,
                           @RequestParam("gpxCoordinates") MultipartFile file,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("routeData", data);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.routeData", bindingResult);

            return "redirect:/add-route";
        }

        routeService.add(data, file);

        return "redirect:/home";
    }

    @GetMapping("route/{id}")
    public ModelAndView route(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("route-details");

        modelAndView.addObject("route", routeService.getDetails(id));

        return modelAndView;
    }

    @GetMapping("/routes/{category}")
    public ModelAndView getRoutesByCategory(@PathVariable("category") CategoryType category) {
        String view = switch (category) {
            case PEDESTRIAN -> "pedestrian-routes";
            case BICYCLE -> "bicycle-routes";
            case MOTORCYCLE -> "motorcycle-routes";
            case CAR -> "car-routes";
        };

        ModelAndView modelAndView = new ModelAndView(view);

        modelAndView.addObject("route",routeService.getRoutesByCategory(category) );

        return modelAndView;
    }
}
