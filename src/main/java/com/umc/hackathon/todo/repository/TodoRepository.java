package com.umc.hackathon.todo.repository;

import com.umc.hackathon.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    // 기본적인 CRUD 기능은 JpaRepository에서 제공하므로 추가적인 메서드가 필요할 경우 정의합니다.

    // 예시: 특정 memberId로 투두 목록을 조회하는 메서드
    List<Todo> findByMemberId(Long memberId);
}