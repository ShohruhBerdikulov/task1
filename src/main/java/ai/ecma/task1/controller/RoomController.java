package ai.ecma.task1.controller;

import ai.ecma.task1.model.Hotel;
import ai.ecma.task1.model.Room;
import ai.ecma.task1.repository.HotelRepository;
import ai.ecma.task1.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/Room")
public class RoomController {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    HotelRepository hotelRepository;


    @GetMapping("/{id}")
    public Object roomOnePageable(@PathVariable Integer id, @RequestParam int page) {
        if (!hotelRepository.findById(id).isPresent()) {
            return "bunday id mavjud emas";
        }
        Pageable pageable = PageRequest.of(page, 10);
        return roomRepository.findAllByHotelId(id, pageable);
    }

    @GetMapping("/all")
    public Object roomAllPageable(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return roomRepository.findAll(pageable);
    }

    @PostMapping("/add")
    public String addRoom(@RequestBody Room newRoom){

        if (!hotelRepository.findById(newRoom.getHotel().getId()).isPresent()) {
            return "hotel no found";
        }
        for (Room room : roomRepository.findAll()) {
            if (room.equals(newRoom)) {
                return "bunday xona oldin qo'shilgan";
            }
        }
        Room room=new Room();
        return addOne(newRoom, room);
    }

    @PutMapping("/add/{roomId}")
    public String setRoom(@PathVariable Integer roomId,@RequestBody Room newRoom){
        if (!roomRepository.findById(roomId).isPresent()) {
            return "room not found";
        }
        Room room=roomRepository.getOne(roomId);
        return addOne(newRoom, room);
    }

    @DeleteMapping("/del/{roomId}")
    public String deleteRoom(@PathVariable Integer roomId){
        if (!roomRepository.findById(roomId).isPresent()) {
            return "room not found";
        }
        roomRepository.deleteById(roomId);
        return "successfully deleted";
    }


    //Helper method
    private String addOne(Room newRoom, Room room) {
        Optional<Hotel> byId = hotelRepository.findById(room.getHotel().getId());
        if (!byId.isPresent()) {
            return "bunday mexmonxona yo'q";
        }
        room.setFloor(newRoom.getFloor());
        room.setNumber(newRoom.getNumber());
        room.setSize(newRoom.getSize());
        room.setHotel(newRoom.getHotel());
        for (Room room1 : roomRepository.findAll()) {
            if (room.equals(room1)) return "This room is already exist";
        }
        roomRepository.save(room);
        return "successfully added ";
    }

}
