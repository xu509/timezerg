<script>
const table_page_node = 1;
const table_size_node = 10;
const domain = "http://127.0.0.1:8081";
// const domain = "http://api.timezerg.com";
const doamin_image = "http://xu-1253743633.file.myqcloud.com";

const url_node_list = domain + "/node/list";
const url_node_add = domain + "/node/save";
const url_node_edit_init = domain + "/node/edit/init";
const url_node_edit_save = domain + "/node/edit/submit";
const url_civilization_list = domain + "/civilization/list";
const url_civilization_add = domain + "/civilization/save";
const url_civilization_edit_init = domain + "/civilization/edit/init";
const url_civilization_edit_save = domain + "/civilization/edit/save";
const url_civilization_upload = domain + "/civilization/upload";
const url_civilization_first = domain + "/civilization/first";
const url_civilization_down = domain + "/civilization/down";
const url_nation_list = domain + "/nation/list";
const url_nation_add = domain + "/nation/save";
const url_nation_edit_init = domain + "/nation/edit/init";
const url_nation_edit_save = domain + "/nation/edit/save";
const url_continent_add = domain + "/continent/save";
const url_index = domain + "/civilization/index";
const url_civilization_index_list = domain + "/civilizationindex/list";
const url_civilization_index_add = domain + "/civilizationindex/add";
const url_civilization_index_del = domain + "/civilizationindex/del";

const url_search_nation = domain + "/nation/search";
const url_search_civilization = domain + "/civilization/search";
const url_search_continent = domain + "/continent/search";

export default {
  table_page_node, //
  table_size_node, //
  domain,
  doamin_image,
  url_index,
  url_node_list,
  url_node_add,
  url_node_edit_init,
  url_node_edit_save,
  url_civilization_list,
  url_civilization_add,
  url_civilization_edit_init,
  url_civilization_upload,
  url_civilization_edit_save,
  url_civilization_first,
  url_civilization_down,
  url_nation_list,
  url_nation_add,
  url_nation_edit_init,
  url_nation_edit_save,
  url_search_nation,
  url_search_civilization,
  url_search_continent,
  url_civilization_index_list,
  url_civilization_index_add,
  url_civilization_index_del
};
</script>

