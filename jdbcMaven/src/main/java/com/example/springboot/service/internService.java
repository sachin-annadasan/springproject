//package com.example.springboot.service;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.modelmapper.ModelMapper;
//import org.springframework.data.rest.webmvc.ResourceNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.example.springboot.entity.InternEntity;
//import com.example.springboot.repository.interrepo;
//import com.example.springboot.requestDto.internRequestDto;
//import com.example.springboot.responseDto.reponseDto;
//
//import jakarta.validation.Valid;
//import lombok.AllArgsConstructor;
//
//@Service
//@AllArgsConstructor
//public class internService {
//
//	private final interrepo repo;
//	private final ModelMapper mapper;
//
//	public List<InternEntity> getAll() {
//		return repo.findAll();
//	}
//
//	public reponseDto create(@Valid internRequestDto internRequestDto) {
//		InternEntity internEntity = new InternEntity();
//		upddateValuesToEntity(internRequestDto,internEntity);
//		internEntity = repo.save(internEntity);
//		reponseDto response = toResponse(internEntity);
//		return response;
//	}
//
//	private reponseDto toResponse(InternEntity internEntity) {
//		reponseDto response = new reponseDto();
//		mapper.map(internEntity, response);
//		return response;
//	}
//
//	public reponseDto update(internRequestDto internreq, Integer id) {
//		reponseDto response = null;
//		Optional<InternEntity> interOptional = repo.findById(id);
//		if (interOptional.isPresent()) {
//			InternEntity internEntity = interOptional.get();
//			upddateValuesToEntity(internreq, internEntity);
//			internEntity = repo.save(internEntity);
//			response = toResponse(internEntity);
//		} else {
//			throw new ResourceNotFoundException("Invalid Id : " + id);
//		}
//		return response;
//	}
//
//	private void upddateValuesToEntity(internRequestDto internreq, InternEntity internEntity) {
//		internEntity.setName(internreq.getName());
//		
//	}
//
//}
