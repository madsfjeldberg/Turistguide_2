package org.example.turistguide_2.controller;

import org.example.turistguide_2.model.TouristAttraction;
import org.example.turistguide_2.repository.TouristRepository;
import org.example.turistguide_2.service.TouristService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(path = "attractions")
public class TouristController {

    private final TouristService service = new TouristService(new TouristRepository());

    @GetMapping
    public String getTouristAttractions(Model model) {
        model.addAttribute("attractions", service.getTouristAttractions());
        return "attractionList";
    }

    @GetMapping(path = "{name}/tags")
    public String getTags(@PathVariable("name") String name, Model model) {
        model.addAttribute("attraction", service.getAttraction(name));
        return "tags";
    }

    @GetMapping(path = "add")
    public String addAttraction(Model model) {
        List<String> tags = Arrays.asList("Historical", "Free", "Nature", "Art", "Food", "Architecture");
        model.addAttribute("tags", tags);
        model.addAttribute("attraction", new TouristAttraction());
        return "add";
    }

    @PostMapping(path = "save")
    public String saveAttraction(@ModelAttribute("attraction") TouristAttraction attraction,
                                 @RequestParam("tags") List<String> tags, Model model) {
        attraction.setTags(tags);
        service.addTouristAttraction(attraction);
        model.addAttribute("attraction", attraction);
        return "redirect:/attractions";
    }

    @GetMapping(path = "{name}/edit")
    public String editAttraction(@PathVariable String name, Model model) {
        List<String> tags = Arrays.asList("Historical", "Free", "Nature", "Art", "Food", "Architecture");
        model.addAttribute("tags", tags);
        TouristAttraction attraction = service.getAttraction(name);
        model.addAttribute("attraction", attraction);
        return "edit";
    }

    @PostMapping(path = "update")
    public String updateAttraction(@ModelAttribute("attraction") TouristAttraction attraction,
                                   @RequestParam(value = "tags", required = false) List<String> tags, Model model) {
        if (tags != null) {
            attraction.setTags(tags);
        }
        service.updateTouristAttraction(attraction);
        model.addAttribute("attraction", attraction);
        return "redirect:/attractions";
    }

    @GetMapping(path = "{name}/delete")
    public String deleteAttraction(@PathVariable String name) {
        service.deleteTouristAttraction(name);
        return "redirect:/attractions";
    }
}
