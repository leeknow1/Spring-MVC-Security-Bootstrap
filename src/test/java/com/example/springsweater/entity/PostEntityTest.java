package com.example.springsweater.entity;

import com.example.springsweater.repository.PostEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class PostEntityTest {

    @Autowired
    private PostEntityRepository repository;

    @Test
    void getAllNotNull(){
        List<PostEntity> posts = repository.findAll();
        assertNotNull(posts);
    }

    @Test
    void idNotNull(){
        for( PostEntity post : repository.findAll()){
            if(post.getId()==null){
                fail(post + " :id is null");
            }
        }
    }

    @Test
    void contentNotNull(){
        for( PostEntity post : repository.findAll()){
            if(post.getContent().isBlank()){
                fail(post + " :content is null");
            }
        }
    }

    @Test
    void contentLessThanMax(){
        for( PostEntity post : repository.findAll()){
            if(post.getContent().length()>50){
                fail(post + " :content is too long");
            }
        }
    }

    @Test
    void tagNotNull(){
        for( PostEntity post : repository.findAll()){
            if(post.getTag().isBlank()){
                fail(post + " :tag is null");
            }
        }
    }

    @Test
    void tagLessThanMax(){
        for( PostEntity post : repository.findAll()){
            if(post.getTag().length()>50){
                fail(post + " :tag is too long");
            }
        }
    }

    @Test
    void setId() {
        PostEntity post = new PostEntity();
        post.setId(100L);
        if(post.getId()!=100){
            fail("setId not working!");
        }
    }

    @Test
    void setContent() {
        PostEntity post = new PostEntity();
        post.setContent("test");
        if(!post.getContent().equals("test")){
            fail("setContent not working!");
        }
    }

    @Test
    void setTag() {
        String test = "test";

        PostEntity post = new PostEntity();
        post.setTag("test");
        if(!post.getTag().equals("test")){
            fail("setTag not working!");
        }
    }
}