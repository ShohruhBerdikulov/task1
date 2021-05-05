package ai.ecma.task1.controller;

import ai.ecma.task1.model.Room;
import ai.ecma.task1.repository.HotelRepository;
import ai.ecma.task1.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Room")
public class RoomController {
    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    RoomRepository roomRepository;
    @GetMapping("/{id}")
    public Object roomPageable(@PathVariable Integer id, @RequestParam int page){
        if (!hotelRepository.findById(id).isPresent()) {
            return "bunday id mavjud emas";
        }
        Pageable pageable =PageRequest.of(page,10);
        return roomRepository.findAllByHotelId(id, pageable);
    }





}
