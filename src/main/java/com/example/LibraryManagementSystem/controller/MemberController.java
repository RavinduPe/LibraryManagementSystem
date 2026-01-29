package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.dto.MemberDto;
import com.example.LibraryManagementSystem.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@ResponseBody
@RequestMapping("api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping()
    public ResponseEntity<MemberDto> createMember(@RequestBody MemberDto memberDto){
        MemberDto response = memberService.createMember(memberDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<MemberDto>> getAllMembers(){
        List<MemberDto> members = memberService.getAllMembers();

        return ResponseEntity.ok(members);
    }

    @GetMapping("{memberId}")
    public ResponseEntity<MemberDto> getMemberBid(@PathVariable String memberId){
        Long id = Long.parseLong(memberId);
        MemberDto memberDto = memberService.getMemberById(id);

        return ResponseEntity.ok(memberDto);
    }

    @PutMapping("{memberId}")
    public  ResponseEntity<String> updateAuthorById(@PathVariable String memberId , @RequestBody Map<String, String> requestBody)
    {
        String newName = requestBody.get("name");
        String updateResponse = memberService.updateMemberName(memberId, newName);

        return ResponseEntity.ok(updateResponse);
    }


    @DeleteMapping("{memberId}")
    public ResponseEntity<String> deleteMemberBYid(@PathVariable String memberId)
    {
        String updateResponse = memberService.MemberDeleteById(memberId);

        return ResponseEntity.ok(updateResponse);
    }
}
