package com.example.demo.src.book;


import com.example.demo.src.book.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class BookDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetBookRes> getBooks(String category){
        if (category == null || category.equals("")){
            return this.jdbcTemplate.query("select category_id, category.name as category_name, book.id as book_id, title, author, image  from book\n" +
                            "join category on category.category_id = category;",
                    (rs, rowNum) -> new GetBookRes(
                            rs.getInt("category_id"),
                            rs.getString("category_name"),
                            rs.getInt("book_id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("image")
                    ));
        }
        return this.jdbcTemplate.query("select category_id, category.name as category_name, book.id as book_id, title, author, image  from book\n" +
                        "join category on category.category_id = category where category.category_id = ?;",
                (rs, rowNum) -> new GetBookRes(
                        rs.getInt("category_id"),
                        rs.getString("category_name"),
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("image")
                ),
                category);
    }

    public int createBook(PostBookReq postBookReq){
        this.jdbcTemplate.update("insert into book (title, author, file, image, category) values (?,?,?,?,?);",
                new Object[]{postBookReq.getTitle(), postBookReq.getAuthor(), postBookReq.getFile(), postBookReq.getImage(), postBookReq.getCategory()}
        );
        return this.jdbcTemplate.queryForObject("select last_insert_id()",int.class);
    }

    public int checkTitle(String title){
        return this.jdbcTemplate.queryForObject("select exists(select title from book where title = ?)",
                int.class,
                title);
    }
}
