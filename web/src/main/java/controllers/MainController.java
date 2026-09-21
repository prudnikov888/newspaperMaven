package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pojos.Category;
import pojos.News;
import services.NewsService;

import jakarta.annotation.security.RolesAllowed;


@Controller
public class MainController {

    @Autowired
    private NewsService newsService;

    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public String showNews (ModelMap modelMap,
                            @RequestParam(value="newsOnPage", defaultValue = "5") int newsOnPage,
                            @RequestParam(value="selectedPage", defaultValue = "1") int selectedPage,
                            @RequestParam(value="sortBy", defaultValue = "postDay") String sortBy) {
        int a = newsService.countAllNews();
        int numberOfPages = (int) Math.ceil(((double) a) / newsOnPage);
        if (selectedPage < 1)
            selectedPage = 1;
        else if (numberOfPages > 0 && selectedPage > numberOfPages)
            selectedPage = numberOfPages;
        modelMap.addAttribute("newsList", newsService.getNewsList(selectedPage, newsOnPage, sortBy));
        modelMap.addAttribute("numberOfPages", numberOfPages);
        modelMap.addAttribute("selectedPage", selectedPage);
        modelMap.addAttribute("newsOnPage", newsOnPage);
        modelMap.addAttribute("sortBy", sortBy);
        if (sortBy.equals("postDay"))
            modelMap.addAttribute("sortByFull", "По дате");
        else
            modelMap.addAttribute("sortByFull", "По категории");
        return "news-list";
    }

    @RolesAllowed("admin")
    @RequestMapping(value = "/news/add", method = RequestMethod.GET)
    public String addNews () {
        return "news-add";
    }

    @RolesAllowed("admin")
    @RequestMapping(value = "/news/{id}/edit", method = RequestMethod.GET)
    public String editNews (ModelMap modelMap, @PathVariable("id") int newsId) {
        modelMap.addAttribute("newsToEdit", newsService.get(newsId));
        return "news-edit";
    }

    @RolesAllowed("admin")
    @RequestMapping(value = "/news/add", method = RequestMethod.POST)
    public String addWriteNews (News news, @RequestParam(value="categoryName") String categoryName ) {
        Category category = new Category();
        category.setCategoryName(categoryName);
        news.setCategory(category);
        newsService.saveOrUpdate(news);
        return "redirect:/news";
    }

    @RolesAllowed("admin")
    @RequestMapping(value = "/news/{id}/edit", method = RequestMethod.POST)
    public String editWriteNews (News news, @RequestParam(value="categoryName") String categoryName ) {
        Category category = new Category();
        category.setCategoryName(categoryName);
        news.setCategory(category);
        newsService.saveOrUpdate(news);
        return "redirect:/news";
    }

    @RolesAllowed("admin")
    @RequestMapping(value = "/news/{id}/delete", method = RequestMethod.POST)
    public String writeNews (@PathVariable("id") int newsId) {
        newsService.delete(newsService.get(newsId));
        return "redirect:/news";
    }

    @RequestMapping(value = "/news/{id}", method = RequestMethod.GET)
    public String showSingleNews (ModelMap modelMap, @PathVariable("id") int newsId) {
        modelMap.addAttribute("singleNews", newsService.get(newsId));
        return "news-single";
    }

    @RequestMapping(value = "/errorPage", method = RequestMethod.GET)
    public String errorPage () {
        return "errorPage";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String logInPage () {
        return "logInPage";
    }



}
