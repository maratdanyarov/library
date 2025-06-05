//package com.danyarov.library.controller;
//
//import com.danyarov.library.config.TestConfig;
//import com.danyarov.library.model.Book;
//import com.danyarov.library.model.Page;
//import com.danyarov.library.model.User;
//import com.danyarov.library.model.UserRole;
//import com.danyarov.library.service.BookService;
//import com.danyarov.library.service.OrderService;
//import com.danyarov.library.util.SessionUtil;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.Arrays;
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {TestConfig.class})
//@WebAppConfiguration
//@ActiveProfiles("test")
//class BookControllerIntegrationTest {
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    private MockMvc mockMvc;
//    private BookService bookService;
//    private OrderService orderService;
//
//    @BeforeEach
//    void setUp() {
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//
//        // Mock services
//        bookService = mock(BookService.class);
//        orderService = mock(OrderService.class);
//    }
//
//    @Test
//    void listBooks_DisplaysBooksPage() throws Exception {
//        // Given
//        Book book1 = new Book();
//        book1.setId(1L);
//        book1.setTitle("Book 1");
//        book1.setAuthor("Author 1");
//
//        Book book2 = new Book();
//        book2.setId(2L);
//        book2.setTitle("Book 2");
//        book2.setAuthor("Author 2");
//
//        Page<Book> bookPage = new Page<>(Arrays.asList(book1, book2), 0, 12, 2L);
//
//        when(bookService.findAllPaginated(0, 12)).thenReturn(bookPage);
//
//        // When & Then
//        mockMvc.perform(get("/books"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("books/list"))
//                .andExpect(model().attributeExists("books"))
//                .andExpect(model().attributeExists("bookPage"))
//                .andExpect(model().attribute("currentPage", 0))
//                .andExpect(model().attribute("pageSize", 12));
//    }
//
//    @Test
//    void listBooks_WithSearch_DisplaysSearchResults() throws Exception {
//        // Given
//        String searchTerm = "Java";
//        Book book = new Book();
//        book.setId(1L);
//        book.setTitle("Java Programming");
//
//        Page<Book> searchResults = new Page<>(Arrays.asList(book), 0, 12, 1L);
//
//        when(bookService.searchPaginated(searchTerm, 0, 12)).thenReturn(searchResults);
//
//        // When & Then
//        mockMvc.perform(get("/books")
//                        .param("search", searchTerm))
//                .andExpect(status().isOk())
//                .andExpect(view().name("books/list"))
//                .andExpect(model().attribute("search", searchTerm))
//                .andExpect(model().attribute("searchParam", "&search=" + searchTerm));
//    }
//
//    @Test
//    void viewBook_DisplaysBookDetails() throws Exception {
//        // Given
//        Long bookId = 1L;
//        Book book = new Book();
//        book.setId(bookId);
//        book.setTitle("Test Book");
//        book.setAuthor("Test Author");
//        book.setAvailableCopies(5);
//
//        when(bookService.findById(bookId)).thenReturn(Optional.of(book));
//
//        // When & Then
//        mockMvc.perform(get("/books/{id}", bookId))
//                .andExpect(status().isOk())
//                .andExpect(view().name("books/detail"))
//                .andExpect(model().attributeExists("book"))
//                .andExpect(model().attribute("book", book));
//    }
//
//    @Test
//    void viewBook_RedirectsToList_WhenBookNotFound() throws Exception {
//        // Given
//        Long bookId = 999L;
//        when(bookService.findById(bookId)).thenReturn(Optional.empty());
//
//        // When & Then
//        mockMvc.perform(get("/books/{id}", bookId))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/books"));
//    }
//
//    @Test
//    void requestBook_RequiresAuthentication() throws Exception {
//        // When & Then
//        mockMvc.perform(post("/books/1/request")
//                        .param("orderType", "HOME"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/login"));
//    }
//
//    @Test
//    void requestBook_CreatesOrder_WhenAuthenticated() throws Exception {
//        // Given
//        Long bookId = 1L;
//        User user = new User();
//        user.setId(1L);
//        user.setEmail("test@example.com");
//        user.setRole(UserRole.READER);
//
//        // Simulate authenticated session
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//                .addFilter((request, response, chain) -> {
//                    request.getSession().setAttribute(SessionUtil.USER_ATTRIBUTE, user);
//                    chain.doFilter(request, response);
//                })
//                .build();
//
//        // When & Then
//        mockMvc.perform(post("/books/{id}/request", bookId)
//                        .param("orderType", "HOME")
//                        .sessionAttr(SessionUtil.USER_ATTRIBUTE, user))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/books/" + bookId))
//                .andExpect(flash().attributeExists("success"));
//    }
//}