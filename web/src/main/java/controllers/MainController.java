package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pojos.Category;
import pojos.News;
import services.NewsService;

import javax.annotation.security.RolesAllowed;


@Controller
public class MainController {

    @Autowired
    private NewsService newsService;

    @RequestMapping(value = "/showNews", method = RequestMethod.GET)
    public String showNews (ModelMap modelMap,
                            @RequestParam(value="newsOnPage", defaultValue = "5") int newsOnPage,
                            @RequestParam(value="selectedPage", defaultValue = "1") int selectedPage,
                            @RequestParam(value="sortBy", defaultValue = "postDay") String sortBy) {
        int a = newsService.countAllNews();
        int numberOfPages = (int) Math.ceil(((double) a) / newsOnPage);
        if (selectedPage < 1)
            selectedPage = 1;
        else if (selectedPage > numberOfPages)
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
        return "showNews";
    }

    @RolesAllowed("admin")
    @RequestMapping(value = "/addNews", method = RequestMethod.GET)
    public String addNews () {
        return "addNews";
    }

    @RolesAllowed("admin")
    @RequestMapping(value = "/editNews", method = RequestMethod.GET)
    public String editNews (ModelMap modelMap, @RequestParam(value="id_for_editing") int newsId) {
        modelMap.addAttribute("newsToEdit", newsService.get(newsId));
        return "editNews";
    }

    @RolesAllowed("admin")
    @RequestMapping(value = "/addWriteNews", method = RequestMethod.GET)
    public String addWriteNews (News news, @RequestParam(value="categoryName") String categoryName ) {
        Category category = new Category();
        category.setCategoryName(categoryName);
        news.setCategory(category);
        newsService.saveOrUpdate(news);
        return "redirect:/showNews.form";
    }

    @RolesAllowed("admin")
    @RequestMapping(value = "/editWriteNews", method = RequestMethod.GET)
    public String editWriteNews (News news, @RequestParam(value="categoryName") String categoryName ) {
        Category category = new Category();
        category.setCategoryName(categoryName);
        news.setCategory(category);
        newsService.saveOrUpdate(news);
        return "redirect:/showNews.form";
    }

    @RolesAllowed("admin")
    @RequestMapping(value = "/delNews", method = RequestMethod.GET)
    public String writeNews (@RequestParam(value="id_for_deleting") int newsId) {
        newsService.delete(newsService.get(newsId));
        return "redirect:/showNews.form";
    }

    @RequestMapping(value = "/showSingleNews", method = RequestMethod.GET)
    public String showSingleNews (ModelMap modelMap, @RequestParam(value="id_for_showing") int newsId) {
        modelMap.addAttribute("singleNews", newsService.get(newsId));
        return "showSingleNews";
    }

    @RequestMapping(value = "/errorPage", method = RequestMethod.GET)
    public String errorPage () {
        return "errorPage";
    }

    @RequestMapping(value = "/logInPage", method = RequestMethod.GET)
    public String logInPage () {
        return "logInPage";
    }



}
