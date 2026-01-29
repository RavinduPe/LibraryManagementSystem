package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dto.AuthorDto;
import com.example.LibraryManagementSystem.dto.MemberDto;
import com.example.LibraryManagementSystem.entity.Author;
import com.example.LibraryManagementSystem.entity.Member;
import com.example.LibraryManagementSystem.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ModelMapper modelMapper;

    public MemberDto createMember(MemberDto memberDto)
    {
        memberRepository.save(modelMapper.map(memberDto, Member.class));
        return memberDto;
    }

    public List<MemberDto> getAllMembers(){
        List<Member> members = memberRepository.findAll();
        return modelMapper.map(members,new TypeToken<List<MemberDto>>(){}.getType());
    }

    public MemberDto getMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with ID: " + id));
        return modelMapper.map(member, MemberDto.class);
    }

    public String MemberDeleteById(String memberId) {
        long id = Long.parseLong(memberId);
        memberRepository.deleteById(id);
        return "Deleted successfully Where Member ID : " + memberId ;
    }

    public String updateMemberName(String memberId, String memberName) {
        Long id = Long.parseLong(memberId);
        int updatedRows = memberRepository.updateMemberByName(id, memberName);

        if (updatedRows == 0) {
            return "No book found with ID: " + memberId;
        }
        return "Updated Member " + memberId + " with new name: " + memberName;
    }

}
