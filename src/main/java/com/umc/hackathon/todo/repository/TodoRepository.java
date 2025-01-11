package com.umc.hackathon.todo.repository;

import com.umc.hackathon.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    // 멤버 아이디와 투두 생성 일시로 투두 목록 조회
    // t.createdAt은 Todo 객체의 createdAt 속성
    // FUNCTION('DATE', t.createdAt)은 데이터베이스에서 createdAt을 DATE 형식으로 변환하려는 함수
    @Query("SELECT t FROM Todo t WHERE t.memberId = :memberId AND FUNCTION('DATE', t.createdAt) = :date")
    List<Todo> findByMemberIdAndCreatedAtDate(@Param("memberId") Long memberId, @Param("date") LocalDate date);

    // 특정 ID로 Todo 삭제
    void deleteById(Long id);
}