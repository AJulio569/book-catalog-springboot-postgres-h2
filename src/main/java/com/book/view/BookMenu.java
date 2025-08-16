package com.book.view;

import com.book.dto.response.AuthorResponse;
import com.book.dto.response.BookResponse;
import com.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookMenu {
    private final BookService bookService;
    private final Scanner scanner = new Scanner(System.in);

    public void mostrarMenu() {
        int opcion;

        do {
            limpiarConsola();
            System.out.println("╔════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                      ✨ BIENVENIDO A API BOOK ✨                              ║");
            System.out.println("╠════════════════════════════════════════════════════════════════════════════════╣");

            System.out.println("╔════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║ 📚 MENÚ DE CONSULTAS DE LIBROS                                                 ║");
            System.out.println("╠════════════════════════════════════════════════════════════════════════════════╣");
            System.out.println("║ 1️⃣. 🔍 Buscar y guardar libro desde Gutendex                                  ║");
            System.out.println("║ 2️⃣. 📘 Ver libros guardados en base de datos                                  ║");
            System.out.println("║ 3️⃣. 📈 Ver los 2 libros más descargados                                       ║");
            System.out.println("║ 4️⃣. 🌍 Buscar libros por idioma (ej: 'en', 'es')                              ║");
            System.out.println("║ 5️⃣. 👤 Buscar libros por nombre de autor                                      ║");
            System.out.println("║ 6️⃣. 📅 Buscar libros publicados antes de cierto año                           ║");
            System.out.println("║ 7️⃣. 📝 Buscar libros por palabra clave en el título                           ║");
            System.out.println("║ 8️⃣. 🚪 Salir                                                                  ║");
            System.out.println("╚════════════════════════════════════════════════════════════════════════════════╝");
            System.out.println("──────────────────────────────────────────");
            System.out.print("👉 Seleccione una opción: ");

            opcion = leerOpcion();
            System.out.println("──────────────────────────────────────────");
            scanner.nextLine();

            switch (opcion) {
                case 1 -> {
                    buscarYGuardarLibro();
                    pausar();
                }
                case 2 -> {
                    listarLibrosGuardados();
                    pausar();
                }
                case 3 -> {
                    verTop2Descargados();
                    pausar();
                }
                case 4 -> {
                    buscarPorIdioma();
                    pausar();
                }
                case 5 -> {
                    buscarPorAutor();
                    pausar();
                }
                case 6 -> {
                    buscarAntesDeAnio();
                    pausar();
                }
                case 7 -> {
                    buscarPorTitulo();
                    pausar();
                }
                case 8 -> System.out.println("👋 Saliendo del sistema...");
                default -> {
                    System.out.println(" ⚠ Opción no válida. Intente de nuevo.");
                    pausar();
                }
            }

        } while (opcion != 8);
    }

    private void buscarYGuardarLibro() {
        System.out.print("\n🔍 Ingrese el título del libro a buscar y guardar: ");
        String title = scanner.nextLine().trim();
        System.out.println();

        if (title.isBlank()) {
            System.out.println("⚠ El nombre del libro no puede estar vacío.");
            return;
        }

         System.out.println("🌐 Consultando en la biblioteca digital de Gutendex. Esto puede tardar unos segundos...");
         mostrarAnimacionCarga("⏳ Buscando y guardando libro", 4, 500);

        try {
            BookResponse savedBook = bookService.saveBookFromGutendex(title);
            if (savedBook == null) {
                System.out.println("⚠️ No se encontró el libro o hubo un error.");
                return;
            }

            mostrarLibrosEnTabla(List.of(savedBook));

            if (savedBook.isAlreadyExists()) {
                System.out.println("\nℹ️ El libro ya existía en la base de datos, no se guardó nuevamente.");
            } else {
                System.out.println("\n✅ Libro guardado en la base de datos.");
            }

        } catch (Exception e) {
            System.out.println("❌ Error al guardar el libro: " + e.getMessage());
        }
    }

    private void listarLibrosGuardados() {
        System.out.println("\n📚 LIBROS GUARDADOS EN BASE DE DATOS");
        System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════\n");

        var libros = bookService.getAllBooks();
        mostrarLibrosEnTabla(libros);
    }

    private void verTop2Descargados() {
        System.out.println("\n📈 Mostrando los 2 libros más descargados:");
        mostrarLibrosEnTabla(bookService.getTop2DownloadedBooks());
    }

    private void buscarPorIdioma() {
        System.out.print("\n🌍 Ingrese el código de idioma (ej: 'en', 'es'): ");
        String code = scanner.nextLine().trim();
        mostrarLibrosEnTabla(bookService.getBooksByLanguage(code));
    }

    private void buscarPorAutor() {
        System.out.print("\n👤 Ingrese el nombre del autor: ");
        String autor = scanner.nextLine().trim();
        mostrarLibrosEnTabla(bookService.getBooksByAuthorName(autor));
    }

    private void buscarAntesDeAnio() {
        System.out.print("\n📅 Ingrese el año límite (ej: 1900): ");
        try {
            int year = Integer.parseInt(scanner.nextLine().trim());
            mostrarLibrosEnTabla(bookService.getBooksBeforeYear(year));
        } catch (NumberFormatException e) {
            System.out.println("⚠ Año inválido. Debe ser un número.");
        }
    }

    private void buscarPorTitulo() {
        System.out.print("\n🔍 Ingrese palabra clave en el título: ");
        String keyword = scanner.nextLine().trim();
        mostrarLibrosEnTabla(bookService.searchBooksByTitle(keyword));
    }

    private void mostrarLibrosEnTabla(List<BookResponse> libros) {
        if (libros == null || libros.isEmpty()) {
            System.out.println("⚠ No se encontraron libros con los criterios especificados.");
            return;
        }

        System.out.println("\n📘 RESULTADOS DE LA CONSULTA");
        System.out.println("════════════════════════════════════════════════════════════════════════════════════════════════════════════════════");
        System.out.println("╔════╦════════════════════════════════════════╦════════════════════════════════════════════╦═════════╦════════════╗");
        System.out.printf("║ %-2s ║ %-38s ║ %-41s ║ %-7s ║ %-10s ║\n", "#", "📗 Título", "👤 Autor(es)", "📅 Año", "⬇ Descargas");
        System.out.println("╠════╬════════════════════════════════════════╬════════════════════════════════════════════╬═════════╬════════════╣");

        for (BookResponse book : libros) {
            String titulo = book.getTitle() != null
                    ? (book.getTitle().length() > 38 ? book.getTitle().substring(0, 37) + "…" : book.getTitle())
                    : "Sin título";

            String autores = book.getAuthors() != null
                    ? book.getAuthors().stream().map(AuthorResponse::getName).collect(Collectors.joining(", "))
                    : "No disponible";

            if (autores.length() > 40) autores = autores.substring(0, 39) + "…";

            String anio = (book.getAuthors() != null && !book.getAuthors().isEmpty())
                    ? formatYear(book.getAuthors().get(0).getBirthYear())
                    : "N/A";

            String descargas = formatDownloads(book.getDownloadCount());

            System.out.printf("║ %-2d ║ %-38s ║ %-42s ║ %-7s ║ %-10s ║\n",
                    book.getId(), titulo, autores, anio, descargas);
        }

        System.out.println("╚════╩════════════════════════════════════════╩════════════════════════════════════════════╩═════════╩════════════╝");
    }

    private int leerOpcion() {
        while (!scanner.hasNextInt()) {
            System.out.println("⚠ Por favor, ingrese un número válido.");
            System.out.println("──────────────────────────────────────────");
            System.out.print("👉 Seleccione una opción: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private void limpiarConsola() {
        for (int i = 0; i < 25; i++) {
            System.out.println();
        }

    }

    private void pausar() {
        System.out.println("──────────────────────────────────────────");
        System.out.println("\n⏸️ Presione ENTER para continuar...");
        System.out.println("──────────────────────────────────────────");
        scanner.nextLine();
    }

    private void mostrarAnimacionCarga(String mensaje, int puntos, int retardoMs) {
        System.out.print(mensaje);
        for (int i = 0; i < puntos; i++) {
            System.out.print(".");
            try {
                Thread.sleep(retardoMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("\n");
    }

    String formatYear(Integer year) {
        if (year == null) return "N/A";
        return year < 0 ? Math.abs(year) + "" : String.valueOf(year);
    }
    String formatDownloads(Integer count) {
        return String.format("%,d", count != null ? count : 0);
    }
}
