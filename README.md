# GREP   

Implementation of a basic grep linux like command.
  
This program follows GNU grep logic flow. It reads raw data into a large buffer, searches the buffer using Boyer-Moore, and only when it finds a match does it go and look for the bounding newlines.
