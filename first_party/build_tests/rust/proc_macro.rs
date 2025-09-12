use proc_macro::TokenStream;

#[proc_macro]
pub fn foo(_item: TokenStream) -> TokenStream {
    TokenStream::new()
}