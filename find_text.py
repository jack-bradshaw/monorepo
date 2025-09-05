
import re

def find_text_in_xml(file_path, target_text):
    try:
        with open(file_path, 'r') as f:
            lines = f.readlines()
        
        extracted_text = ""
        line_mapping = [] # Map character index in extracted_text to line number in file
        
        for i, line in enumerate(lines):
            # Simple regex to extract text content from <text ...>content</text>
            match = re.search(r'<text[^>]*>(.*?)</text>', line)
            if match:
                content = match.group(1)
                extracted_text += content
                # Map each character of content to the line number
                for _ in content:
                    line_mapping.append(i + 1)
            elif '<text' in line and '</text>' not in line:
                 # Handle multi-line text tags if any (though unlikely based on view_file)
                 pass
        
        # Search for target text
        start_search_index = 0
        while True:
            index = extracted_text.find(target_text, start_search_index)
            if index == -1:
                break
            
            print(f"Found '{target_text}' starting at index {index}")
            start_line = line_mapping[index]
            end_line = line_mapping[index + len(target_text) - 1]
            print(f"Line range: {start_line} - {end_line}")
            
            # Print context
            context_start = max(0, index - 100)
            context_end = min(len(extracted_text), index + len(target_text) + 200)
            print(f"Context: {extracted_text[context_start:context_end]}")
            print("-" * 20)
            
            start_search_index = index + 1
            
        if start_search_index == 0:
            print(f"'{target_text}' not found.")
            # Print some text to see what we have
            print(f"Total extracted text length: {len(extracted_text)}")
            print(f"First 500 chars: {extracted_text[:500]}")

    except Exception as e:
        print(f"Error: {e}")

    with open('extracted_text.txt', 'w') as f:
        f.write(extracted_text)
    print("Wrote extracted text to extracted_text.txt")

find_text_in_xml('/Users/jack/monorepo/first_party/site/resumes/Application_2.xml', 'Department of Human Services')
