use proc_macro::TokenStream;

/// No-op proc macro, used to ensure Rust build infra works.
#[proc_macro]
pub fn foo(_item: TokenStream) -> TokenStream {
    TokenStream::new()
}