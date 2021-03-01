package com.example.demo.src.book;

import com.example.demo.src.book.model.GetBookRes;
import com.example.demo.src.user.UserDao;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class BookProvider {

    private final BookDao bookDao;
    private final JwtService jwtService;

    private JdbcTemplate jdbcTemplate;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    public BookProvider(BookDao bookDao, JwtService jwtService) {
        this.bookDao = bookDao;
        this.jwtService = jwtService;
    }

    public List<GetBookRes> getBooks(String category){
        List<GetBookRes> getBooksRes = bookDao.getBooks(category);
        return getBooksRes;
    }

    public int checkTitle(String title) {return bookDao.checkTitle(title); }
}