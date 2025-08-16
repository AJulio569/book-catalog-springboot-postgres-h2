package com.book;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.book.view.BookMenu;

@SpringBootApplication
@RequiredArgsConstructor
public class ApiBookApplication implements CommandLineRunner {
    private final BookMenu bookMenu;
	public static void main(String[] args) {
		SpringApplication.run(ApiBookApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
        bookMenu.mostrarMenu();

	}
}
