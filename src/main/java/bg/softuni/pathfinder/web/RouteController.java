package bg.softuni.pathfinder.web;

import bg.softuni.pathfinder.model.CategoryType;
import bg.softuni.pathfinder.model.Level;
import bg.softuni.pathfinder.service.RouteService;
import bg.softuni.pathfinder.service.dto.RouteShortInfoDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class RouteController {

    private RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }


    @GetMapping("/routes")
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
}
