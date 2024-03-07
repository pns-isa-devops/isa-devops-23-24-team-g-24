import {IsBoolean, IsNotEmpty, IsPositive, IsString} from 'class-validator';

export class BookReceiptDto {
  constructor(bookReceiptId: string, dateBook: string, nameActivity: string, namePartner: string) {
    this.bookReceiptId = bookReceiptId;
    this.dateBook = dateBook;
    this.nameActivity = nameActivity;
    this.namePartner = namePartner;
  }

  @IsNotEmpty()
  @IsString()
  bookReceiptId: string;

  @IsNotEmpty()
  @IsString()
  dateBook: string;

  @IsNotEmpty()
  @IsString()
  nameActivity: string;

  @IsNotEmpty()
  @IsString()
  namePartner: string;
}
