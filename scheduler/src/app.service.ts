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

    var dateActuelle = new Date();
    var jour = dateActuelle.getDate();
    var mois = dateActuelle.getMonth() + 1; 
    var heure = dateActuelle.getHours();
    var minute = dateActuelle.getMinutes();
    var seconde = dateActuelle.getSeconds();
    var timeActivity = jour + "/" + mois + " " + heure + ":" + minute + ":" + seconde;

    bookReceiptDto = new BookReceiptDto(
      'RECEIPT:' + randomUUID(),
      timeActivity,
      bookRequestDto.nameActivity,
      bookRequestDto.namePartner,
    );
    
    if(this.isBooked(timeActivity, bookRequestDto.nameActivity, bookRequestDto.namePartner)){
        throw new ActivityAlreadyBooked(bookRequestDto.nameActivity, bookRequestDto.namePartner, timeActivity);
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
