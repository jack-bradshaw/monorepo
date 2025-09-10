from demoparser2 import DemoParser
from python.runfiles import Runfiles

def main():
  r = Runfiles.Create()
  data_path = r.Rlocation("com_jackbradshaw/first_party/cheaterfinder/game.dem")
  parser = DemoParser(data_path)
  events = parser.parse_event("yaw",  player= ["X", "Y"], other=["total_rounds_played"])
  #print(type(out))



if __name__ == "__main__":
    main()