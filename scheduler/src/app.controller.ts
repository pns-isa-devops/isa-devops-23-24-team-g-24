import {
  Body,
  Controller,
  Get,
  HttpException,
  HttpStatus,
  Post,
} from '@nestjs/common';

import { AppService } from './app.service';
import { PaymentRequestDto } from './dto/paymentRequest.dto';
import { PaymentReceiptDto} from "./dto/paymentReceipt.dto";

import { BookReceiptDto} from "./dto/bookReceipt.dto";
import { BookRequestDto} from "./dto/bookRequest.dto";

@Controller('scheduler')
export class AppController {
  constructor(private readonly appService: AppService) {}

  @Get()
  getAllTransactions(): PaymentReceiptDto[] {
    return this.appService.findAll();
  }

  @Post()
  payByCreditCard(@Body() paymentRequestDto: PaymentRequestDto): PaymentReceiptDto {
    try {
      return this.appService.pay(paymentRequestDto);
    } catch (e) {
      throw new HttpException(
        'business error: ' + e.message,
        HttpStatus.BAD_REQUEST,
      );
    }
  }

  @Get()
    getAllBookTransactions(): BookReceiptDto[]{
    return this.appService.findAllBook();
  }

  @Post()
    bookActivity(@Body() bookRequestDto: BookRequestDto): BookReceiptDto {
    try {
      return this.appService.book(bookRequestDto);
    } catch (e) {
      throw new HttpException(
        'business error: ' + e.message,
        HttpStatus.BAD_REQUEST,
      );
    }
  }

  @Get()
    getAllBooksPartner(@Body() namePartner: string): BookReceiptDto[]{
      return this.appService.findBooksPartner(namePartner);
  }

}
