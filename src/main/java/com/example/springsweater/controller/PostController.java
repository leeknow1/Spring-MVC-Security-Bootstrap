package com.example.springsweater.controller;

import com.example.springsweater.entity.PostEntity;
import com.example.springsweater.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("")
    public String allPost(Model model, @RequestParam(defaultValue = "1", required = false, value = "page") int page,
                          @RequestParam(defaultValue = "", required = false, value = "content") String content,
                          @RequestParam(defaultValue = "", required = false, value = "tag") String tag
                          ){
        return postService.findPost(model, content, tag, page);
    }

    @GetMapping("/add")
    public String getAddPost(@ModelAttribute("test") PostEntity postEntity){
        return "postAdd";
    }

    @PostMapping("/add")
    public String makePost(@ModelAttribute("test") @Valid PostEntity postEntity, BindingResult bindingResult){
        return postService.addPost(postEntity, bindingResult);
    }

    @GetMapping("/edit/{id}")
    public String getToEditPost(Model model, @PathVariable long id){
        return postService.getEditPost(model, id);
    }

    @PostMapping("/edit/{id}")
    public String editPost(@ModelAttribute("edit") @Valid PostEntity postEntity, BindingResult bindingResult, @PathVariable long id) {
        return postService.editPost(postEntity, bindingResult, id);
    }

    @GetMapping("/delete/{id}")
    public String getToDelete( @PathVariable long id){
        return postService.getToDelete(id);
    }
}
