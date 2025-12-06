package Doska.data;

import lombok.Data;

// Конструктор объявления
@Data
public class Announcement {
    private String name; //имя
    private String description; //описание товара
    private int price; //стоимость
    private String photoPath; //путь к фото
}
