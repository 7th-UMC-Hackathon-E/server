package com.umc.hackathon.room.service;

import com.umc.hackathon.room.dto.EnterRoomDTO;
import com.umc.hackathon.room.dto.RoomDTO;
import com.umc.hackathon.room.dto.RoomListDTO;
import com.umc.hackathon.room.dto.UserDTO;
import com.umc.hackathon.room.entity.Room;
import com.umc.hackathon.room.entity.UserStudy;
import com.umc.hackathon.room.repository.RoomRepository;
import com.umc.hackathon.room.repository.UserStudyRepository;
import com.umc.hackathon.todo.entity.Todo;
import com.umc.hackathon.todo.repository.TodoRepository;
import com.umc.hackathon.user.entity.User;
import com.umc.hackathon.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final TodoRepository todoRepository;
    private final UserStudyRepository userStudyRepository;
    public RoomService(RoomRepository roomRepository, UserRepository userRepository,
                       TodoRepository todoRepository, UserStudyRepository userStudyRepository) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.todoRepository = todoRepository;
        this.userStudyRepository = userStudyRepository;
    }

    public void enterRoom(EnterRoomDTO enterRoomDTO) {
        // Room 조회 및 검증
        Room room = roomRepository.findById(enterRoomDTO.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        // User 조회 및 검증
        User user = userRepository.findById(enterRoomDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Todo 리스트 조회
        List<Todo> todoList = todoRepository.findByMemberIdAndCreatedAtDate(enterRoomDTO.getUserId(), LocalDate.now());

        // 진행률 계산 및 통계
        UserStudy userStudy = calculateUserStudyStatistics(room, user, todoList);

        // UserStudy 저장
        userStudyRepository.save(userStudy);

        // Room 인원 업데이트
        room.setNumberOfPeople(room.getNumberOfPeople() + 1);
        roomRepository.save(room);
    }

    public void leaveRoom(Long userId) {
        UserStudy userStudy = userStudyRepository.findById(userId).orElseThrow();
        Room room = roomRepository.findById(userStudy.getRoom().getRoomId()).orElseThrow();
        room.setNumberOfPeople(room.getNumberOfPeople() - 1);
        roomRepository.save(room);
        userStudyRepository.delete(userStudy);
    }

    public RoomDTO getRoomDetail(Long userId, Long roomId) {
        RoomDTO roomDTO = new RoomDTO();

        // Room 조회 및 UserStudy 리스트 가져오기
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new IllegalArgumentException("Room not found"));
        List<UserStudy> userStudies = userStudyRepository.findAllByRoom(room);

        // UserStudy를 progress 기준으로 내림차순 정렬
        userStudies.sort(Comparator.comparingDouble(UserStudy::getProgress).reversed());

        List<UserDTO> users = new ArrayList<>();
        for (UserStudy userStudy : userStudies) {
            User user = userStudy.getUser();
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getUserId());
            userDTO.setName(user.getName());
            users.add(userDTO);
        }

        int rank = userStudies.stream()
                .map(UserStudy::getUser)
                .map(User::getUserId)
                .toList()
                .indexOf(userId) + 1;
        roomDTO.setUsers(users);
        roomDTO.setNumberOfPeople(users.size());
        roomDTO.setTodos(todoRepository.findByMemberIdAndDate(userId, LocalDate.now()));
        roomDTO.setRanking(rank);

        return roomDTO;
    }



    public RoomListDTO getRoomList() {
        List<Room> roomList = roomRepository.findAll();
        RoomListDTO roomListDTO = new RoomListDTO();
        roomListDTO.setRooms(roomList);
        return roomListDTO;
    }


    private UserStudy calculateUserStudyStatistics(Room room, User user, List<Todo> todoList) {
        // 완료 여부에 따른 분류
        Map<Boolean, List<Todo>> partitionedTodos = todoList.stream()
                .collect(Collectors.partitioningBy(Todo::isStatus));

        int numberDone = partitionedTodos.get(true).size();
        int numberAll = todoList.size();
        double progress = numberAll > 0 ? ((double) numberDone / numberAll) * 100 : 0;

        // UserStudy 생성 및 값 설정
        UserStudy userStudy = new UserStudy();
        userStudy.setRoom(room);
        userStudy.setUser(user);
        userStudy.setNumberAll(numberAll);
        userStudy.setNumberDone(numberDone);
        userStudy.setProgress(progress);

        return userStudy;
    }
}

