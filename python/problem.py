from os.path import join
import xml.etree.ElementTree as ET


class Problem(object):

    def read_xml(self, file):
        tree = ET.parse(file)
        root = tree.getroot()
        print(root)


    def eval(self):
        self.read_xml(join("..", "java", "res", "cte.xml"))
    

if __name__ == "__main__":
    Problem().eval()

