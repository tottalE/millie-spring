package com.example.demo.src.shelf;

import com.example.demo.src.shelf.model.GetTotalShelfRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseResponse;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/shelfs")
public class ShelfController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ShelfProvider shelfProvider;
    @Autowired
    private final ShelfService shelfService;
    @Autowired
    private final JwtService jwtService;

    public ShelfController(ShelfProvider shelfProvider, ShelfService shelfService, JwtService jwtService) {
        this.shelfProvider = shelfProvider;
        this.shelfService = shelfService;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @GetMapping("")
    public BaseResponse<GetTotalShelfRes> getShelfBooks(@RequestParam(required = false, defaultValue = "1") String sequence){
        int Intsequence = Integer.parseInt(sequence);
        if(Intsequence >= 7|| 1 > Intsequence){
            return new BaseResponse<>(GET_SHELFS_INVAILD_NUMBER);
        }

        GetTotalShelfRes getShelfBooksResList = shelfProvider.GetShelfBookList(Intsequence);
        return new BaseResponse<>(getShelfBooksResList);
    }
}