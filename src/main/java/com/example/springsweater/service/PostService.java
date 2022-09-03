package com.example.springsweater.service;

import com.example.springsweater.entity.PostEntity;
import com.example.springsweater.repository.PostEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final PostEntityRepository postEntityRepository;

    public PostService(PostEntityRepository postEntityRepository) {
        this.postEntityRepository = postEntityRepository;
    }

    public String findPost(Model model,@RequestParam(defaultValue = "", required = false, value = "content") String content,
                           @RequestParam(defaultValue = "", required = false, value = "tag") String tag,
                           @RequestParam(defaultValue = "1", required = false, value = "page") int page){
        Page<PostEntity> findPost;
        Pageable pageable = PageRequest.of(page -1, 12);

        if(!content.isBlank() && tag.isBlank()){
            findPost = postEntityRepository.findByContent(content, pageable);
        } else if(content.isBlank() && !tag.isBlank()){
            findPost = postEntityRepository.findByTag(tag, pageable);
        } else if(!content.isBlank() && !tag.isBlank()){
            findPost = postEntityRepository.findByContentAndTag(content, tag, pageable);
        } else {
            findPost = postEntityRepository.findAll(pageable);
        }

        return doThis(findPost, page, model);
    }

    public String doThis(Page<PostEntity> findPost, int page, Model model){

        int totalP = findPost.getTotalPages();
        long totalItems = findPost.getTotalElements();
        List<PostEntity> list = findPost.getContent();

        model.addAttribute("pageList", getTotalPageList(totalP));
        model.addAttribute("threePage", getThreePages(page, totalP));

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalP);
        model.addAttribute("totalItems", totalItems);

        model.addAttribute("posts", list);

        return "post";
    }


    public String addPost(@Valid @ModelAttribute("test") PostEntity postEntity, BindingResult result){

        if(result.hasErrors()){
            return "postAdd";
        }

        postEntityRepository.save(postEntity);

        return "redirect:/post";
    }

    public String getEditPost(Model model, long id){
        PostEntity postToEdit = postEntityRepository.findById(id);
        model.addAttribute("edit", postToEdit);
        return "postEdit";
    }

    public String editPost(@ModelAttribute("edit") @Valid PostEntity postEntity, BindingResult result, long id){
        if(result.hasErrors()){
            return "postEdit";
        }

        PostEntity editPost = postEntityRepository.findById(id);
        editPost.setContent(postEntity.getContent());
        editPost.setTag(postEntity.getTag());

        postEntityRepository.save(editPost);

        return "redirect:/post";
    }

    public String getToDelete(long id){
        postEntityRepository.deleteById(id);

        return "redirect:/post";
    }

    public List<Integer> getTotalPageList(int totalPages){
        List<Integer> totalPagesList = new ArrayList<>();
        int j=1;
        for(int i=0; i<totalPages; i++){
            totalPagesList.add(i, j);
            j++;
        }
        return totalPagesList;
    }

    public List<Integer> getThreePages(int page, int totalP) {
        List<Integer> pagesList = new ArrayList<>();
        if (page == 1) {
            pagesList.add(1);
            pagesList.add(2);
            pagesList.add(3);
        }

        if (page > 1) {
            pagesList.clear();
            pagesList.add(page - 1);
            pagesList.add(page);
            pagesList.add(page + 1);
        }

        if (page == (totalP - 1) || page == totalP) {
            pagesList.clear();
            pagesList.add(totalP - 2);
            pagesList.add(totalP - 1);
            pagesList.add(totalP);
        }

        if(page > totalP) {
            pagesList.clear();
        }

        return pagesList;
    }
}
