from Utils.Crypto import CryptoToken, Operation
from Utils.PrinterManager import PrinterManager as Printer


def execute():
    try:
        entries = ["Daniel Eduardo Bautista Fuentes", "Pablo Alejandro Sanchez", "Yo solo quiero comer un helado"]
        crypto_tokens = CryptoToken(entries, Operation.ENCRYPT)

        encrypted_entries = crypto_tokens.solve()

        # reset to test decrypt
        entries = []
        # PRINT ENCRYPTED MATRICES
        for encrypted_entry in encrypted_entries:
            Printer.custom_print_array(encrypted_entry)
            entries.append(encrypted_entry)

        # DECRYPT MATRICES
        crypto_tokens = CryptoToken(entries, Operation.DECRYPT)
        decrypted_values = crypto_tokens.solve()
        for decrypted_value in decrypted_values:
            Printer.custom_print(decrypted_value)

    except Exception as e:
        Printer.custom_print(
            f'Error al ejecutar el código: {e}\nAsegúrate de que el archivo exista y que termine en salto de linea')


def main():
    try:
        execute()
    except Exception as e:
        Printer.custom_print(f'Error: {e}')


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    main()
