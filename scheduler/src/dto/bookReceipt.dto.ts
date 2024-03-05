import {IsBoolean, IsNotEmpty, IsPositive, IsString} from 'class-validator';

export class BookReceiptDto {
  constructor(bookReceiptId: string, isAuthorized: boolean) {
    this.bookReceiptId = bookReceiptId;
    this.isAuthorized = isAuthorized;
  }

  @IsNotEmpty()
  @IsString()
  bookReceiptId: string;

  @IsNotEmpty()
  @IsBoolean()
  isAuthorized: boolean;
}
