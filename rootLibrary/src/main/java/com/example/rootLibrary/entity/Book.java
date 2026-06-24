package com.example.rootLibrary.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book {

	// 貸出状態を表す内部Enum
	public enum BookStatus {
		AVAILABLE, LENDING
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String author;

	private Integer publishedYear;

	// Enumを文字列としてDBに保存
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BookStatus status = BookStatus.AVAILABLE;

	private String borrower;

	private LocalDate dueDate;

	// --- ビジネスロジック ---

	public boolean isAvailable() {
		return BookStatus.AVAILABLE.equals(this.status);
	}

	// 引数で基準日を受け取るように変更（テストしやすくなります）
	public boolean isOverdue(LocalDate targetDate) {
		return BookStatus.LENDING.equals(this.status)
				&& this.dueDate != null
				&& this.dueDate.isBefore(targetDate);
	}

	// Thymeleafの ${book.overdue} はこのメソッドを自動的に呼び出します
	public boolean isOverdue() {
		return isOverdue(LocalDate.now());
	}
}