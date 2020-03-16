package com.enjoy.mathero.service.impl;

import com.enjoy.mathero.io.entity.FriendshipEntity;
import com.enjoy.mathero.io.repository.FriendshipRepository;
import com.enjoy.mathero.service.FriendshipService;
import com.enjoy.mathero.shared.dto.FriendshipDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendshipServiceImpl implements FriendshipService {

    @Autowired
    FriendshipRepository friendshipRepository;

    @Override
    public FriendshipDto createFriendship(FriendshipDto friendshipDto) {

        ModelMapper modelMapper = new ModelMapper();
        FriendshipEntity friendshipEntity = modelMapper.map(friendshipDto, FriendshipEntity.class);

        FriendshipEntity savedDetails = friendshipRepository.save(friendshipEntity);
        FriendshipDto returnValue = modelMapper.map(savedDetails, FriendshipDto.class);

        return returnValue;
    }
}
