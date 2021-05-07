package ai.ecma.task1.controller;

import ai.ecma.task1.model.Hotel;
import ai.ecma.task1.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Hotel")
public class HotelController {
    @Autowired
    HotelRepository hotelRepository;

    @GetMapping("/{id}")
    public Object hotelOnePageable(@PathVariable Integer id, @RequestParam int page) {
        if (!hotelRepository.findById(id).isPresent()) {
            return "bunday id mavjud emas";
        }
        return hotelRepository.findById(id);
    }

    @GetMapping("/all")
    public Object hotelAllPageable(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return hotelRepository.findAll(pageable);
    }

    @PostMapping("/add")
    public String addHotel(@RequestBody Hotel newHotel) {
        for (Hotel hotel : hotelRepository.findAll()) {
            if (hotel.getName().equals(newHotel.getName())) {
                return "this hotel allready exist";
            }
        }
        hotelRepository.save(newHotel);
        return "success";
    }

    @PutMapping("/add/{hotelId}")
    public String setHotel(@PathVariable Integer hotelId, @RequestBody Hotel newHotel) {
        if (hotelRepository.findById(hotelId).isPresent()) {
            hotelRepository.save(newHotel);
        }
        return "Hotel not found";

    }

    @DeleteMapping("/del/{id}")
    public String deleteHotel(@PathVariable Integer hotelId) {
        if (!hotelRepository.findById(hotelId).isPresent()) {
            return "room not found";
        }
        hotelRepository.deleteById(hotelId);
        return "successfully deleted";
    }

}
