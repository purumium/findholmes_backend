package org.detective.repository;


import org.detective.entity.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {

    Optional<ChatRoom> findByEstimateId(Long estimateId);

    @Query("{ 'participants': { $elemMatch: { 'user_id': ?0, 'role': 'c' } } }")
    List<ChatRoom> findByUserIdAndRoleIsClient(Long userId);

    @Query("{ 'participants': { $elemMatch: { 'user_id': ?0, 'role': 'd' } } }")
    List<ChatRoom> findByUserIdAndRoleIsDetective(Long userId);
}
