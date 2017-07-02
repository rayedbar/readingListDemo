package com.rayed.readinglist.web;

import com.rayed.readinglist.AmazonProperties;
import com.rayed.readinglist.domain.Book;
import com.rayed.readinglist.repository.ReadingListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author: rayed
 * @since: 6/29/17
 */
@Controller
@RequestMapping("/")
@ConfigurationProperties(prefix = "amazon")
public class ReadingListController {

    private Logger log = LoggerFactory.getLogger(ReadingListController.class);

    private ReadingListRepository readingListRepository;

    private AmazonProperties amazonProperties;

    @Autowired
    public ReadingListController(ReadingListRepository readingListRepository, AmazonProperties amazonProperties) {
        this.readingListRepository = readingListRepository;
        this.amazonProperties = amazonProperties;
    }

    @RequestMapping(value = "/{reader}", method = RequestMethod.GET)
    public String readersBooks(@PathVariable("reader") String reader, Model model) {
        List<Book> readingList = readingListRepository.findByReader(reader);

        model.addAttribute("books", readingList);
        model.addAttribute("associateId", amazonProperties.getAssociateId());

        return "readingList";
    }

    @RequestMapping(value = "/{reader}", method = RequestMethod.POST)
    public String addToReadingList(@PathVariable("reader") String reader, Book book) {
        log.info("{} added {} to his reading list", reader, book.getTitle());

        book.setReader(reader);
        readingListRepository.save(book);

        return "redirect:/{reader}";
    }
}
