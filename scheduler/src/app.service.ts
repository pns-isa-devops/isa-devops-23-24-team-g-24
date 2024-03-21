import { Injectable } from '@nestjs/common';

import { PaymentRequestDto } from './dto/paymentRequest.dto';
import { PaymentReceiptDto } from './dto/paymentReceipt.dto';
import { PaymentRejectedException } from './exceptions/payment-rejected-exception';
import { randomUUID } from 'crypto';
import {BookReceiptDto} from "./dto/bookReceipt.dto";
import {BookRequestDto} from "./dto/bookRequest.dto";
import {ActivityAlreadyBooked} from "./exceptions/activity-already-booked";

@Injectable()
export class AppService {

  private books: Array<BookReceiptDto>;

  constructor() {
    this.books = [];
  }

  findAllBook(): BookReceiptDto[]{
      return this.books;
  }

book(bookRequestDto: BookRequestDto): BookReceiptDto {
    let bookReceiptDto: BookReceiptDto;
    bookReceiptDto = new BookReceiptDto(
      'RECEIPT:' + randomUUID(),
      bookRequestDto.dateBook,
      bookRequestDto.nameActivity,
      bookRequestDto.namePartner,
    );
    if(bookRequestDto.namePartner === 'magicPartner'){
      this.books.push(bookReceiptDto);
      console.log(
        'Book accepted(' +
          bookReceiptDto.bookReceiptId +
          '): ' +
          bookReceiptDto.dateBook,
      );
      return bookReceiptDto;
    }

    if(this.isBooked(bookRequestDto.dateBook, bookRequestDto.nameActivity, bookRequestDto.namePartner)){
        throw new ActivityAlreadyBooked(bookRequestDto.nameActivity, bookRequestDto.namePartner, bookRequestDto.dateBook);
    }
    this.books.push(bookReceiptDto);
    console.log(
      'Book accepted(' +
        bookReceiptDto.bookReceiptId +
        '): ' +
        bookReceiptDto.dateBook,
    );
    return bookReceiptDto;
  }

  isBooked(dateBook: string, nameActivity: string, namePartner: string): boolean {
    return this.books.some(
      (book) =>
        book.dateBook === dateBook &&
        book.nameActivity === nameActivity &&
        book.namePartner === namePartner,
    );
  }

    findBooksPartner(namePartner: string) {
        return this.books.filter((book) => book.namePartner === namePartner);
    }
}
