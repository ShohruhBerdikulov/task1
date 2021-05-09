package ai.ecma.task1.controller;

import ai.ecma.task1.model.Hotel;
import ai.ecma.task1.model.Room;
import ai.ecma.task1.model.RoomDto;
import ai.ecma.task1.repository.HotelRepository;
import ai.ecma.task1.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @DeleteMapping("/del/{roomId}")
    public String deleteRoom(@PathVariable Integer roomId) {
        if (!roomRepository.findById(roomId).isPresent()) {
            return "room not found";
        }
        roomRepository.deleteById(roomId);
        return "successfully deleted";
    }

    @PostMapping("/add")
    public String addRoom(@RequestBody Room newRoom) {

        if (!hotelRepository.findById(newRoom.getHotel().getId()).isPresent()) {
            return "hotel no found";
        }

        for (Room room : roomRepository.findAll()) {
            if (newRoom.getHotel().getId().equals(room.getHotel().getId())
                    &&
                    newRoom.getNumber().equals(room.getNumber()))
                return "siz qo'shmoqchi bo'lgan room shu mehmonxonada mavjud mavjud";
        }
        roomRepository.save(newRoom);
        return "room successfully added";
    }

    @PutMapping("/add/{roomId}")
    public String setRoom(@PathVariable Integer roomId, @RequestBody Room newRoom) { //Faqatgina Hotel Id jo'natiladi
        Optional<Room> byId = roomRepository.findById(roomId);
        if (!byId.isPresent()) {
            return "room not found";
        }
        Room roomOld = byId.get();
        if (newRoom.getNumber() != null) roomOld.setNumber(newRoom.getNumber());
        if (newRoom.getFloor() != null) roomOld.setFloor(newRoom.getFloor());
        if (newRoom.getSize() != null) roomOld.setSize(newRoom.getSize());
        if (newRoom.getHotel().getId() != null) roomOld.getHotel().setId(newRoom.getHotel().getId());

        int count = 0;
        for (Room room : roomRepository.findAll()) {
            if (roomOld.getHotel().getId().equals(room.getHotel().getId())
                    &&
                    newRoom.getNumber().equals(room.getNumber()))
                count++; //nechta shunday room borligi aniqlanadi 1 ta bo'lishi kk
        }
        if (count == 1) {
            roomRepository.save(roomOld);
            return "success";
        } else return "Error";
    }
}
